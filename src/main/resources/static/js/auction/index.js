console.log("Script loaded!");

/**
 * @file Manages real-time bidding functionality for auction updates using WebSocket.
 * @module auctionLive
 */

/** 
 * WebSocket client connection using SockJS and STOMP protocol
 * @type {SockJS}
 */
const socket = new SockJS('/auction/websocket');

/**
 * STOMP client over WebSocket connection
 * @type {Stomp.Client}
 */
const stompClient = Stomp.over(socket);

/**
 * ID of the current auction extracted from DOM
 * @type {string}
 */
const auctionId = document.getElementById('auctionId').textContent;
console.log("Auction ID:", auctionId);

/**
 * Initializes WebSocket connection and subscribes to auction updates
 * @function
 * @listens Stomp#connect
 */
stompClient.connect({}, function (frame) {
  console.log('Connected: ' + frame);

  /**
   * Subscribes to auction updates channel
   * @function
   * @param {Message} response - STOMP message containing auction data
   */
  stompClient.subscribe(`/topic/auction/updates/${auctionId}`, (response) => {
    const auctionUpdatedResponse = JSON.parse(response.body);
    updateAuctionUI(auctionUpdatedResponse);
  });
}, (error) => {
  console.error('Connection to live auction service failed:', error);
});

/**
 * Handles browser close/tab refresh to clean up WebSocket connection
 * @listens window:beforeunload
 */
window.addEventListener('beforeunload', () => {
  if (stompClient) stompClient.disconnect();
});

/**
 * Submits a new offer for the current auction
 * @async
 * @function
 * @throws {Error} When offer submission fails
 * @example
 * placeOffer(); // Called when user clicks bid button
 */
async function placeOffer() {
  const offerInput = document.getElementById('offerAmount');
  const offerAmount = parseFloat(offerInput.value);
  const button = document.getElementById('offerButton');
  button.disabled = true;

  try {
    const response = await fetch(`/auction/offer/place`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        auctionId: parseInt(auctionId),
        offerPrice: offerAmount
      })
    });

    const responseData = await response.json();

    if (!response.ok) {
      // Server returned error response (4xx/5xx)
      if (responseData.error) {
        displayErrors(responseData.messages); // Display errors from the server
      } else {
        throw new Error(responseData.message || 'Offer failed');
      }
    } else {
      // Success case
      offerInput.value = '';
      alert("Offer placed successfully!");
    }
  } catch (error) {
    console.error('Error:', error);
    displayErrors([error.message || "Failed to place offer"]); // Display generic error
  } finally {
    button.disabled = false;
  }
}

/**
 * Updates UI with latest auction data
 * @function
 * @param {Object} offerAuctionResponse - Auction update payload
 * @param {number} offerAuctionResponse.currentPrice - Current auction price
 * @example
 * updateAuctionUI({ currentPrice: 1500.00 });
 */
function updateAuctionUI(offerAuctionResponse) {
  console.log(offerAuctionResponse);
  document.getElementById('current-price').innerText =
    offerAuctionResponse.currentPrice.toLocaleString('en-US', {
      style: 'currency',
      currency: 'USD',
    });
}

// Function to display error messages
function displayErrors(errors) {
  const errorAlert = document.getElementById('errorAlert');
  const errorList = document.getElementById('errorList');

  // Clear any existing errors
  errorList.innerHTML = '';

  // Add each error to the list
  errors.forEach(error => {
    const listItem = document.createElement('li');
    listItem.className = 'list-group-item text-danger bg-transparent border-0';
    listItem.textContent = error;
    errorList.appendChild(listItem);
  });

  // Show the error alert
  errorAlert.classList.remove('d-none');
}

// Function to dismiss the error alert
function dismissError() {
  const errorAlert = document.getElementById('errorAlert');
  errorAlert.classList.add('d-none');
}

