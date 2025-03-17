/**
 * @file Manages real-time bidding functionality for auction updates using WebSocket.
 * @module auctionLive
 */

// Importing Stomp and SockJS libraries for WebSocket communication
const socket = new SockJS('/ws/auction/websocket');
const stompClient = Stomp.over(socket);

// Necessary DOM elements5
const auctionId = document.getElementById('auctionId').textContent;
const offerInput = document.getElementById('offerAmount');
const offerButton = document.getElementById('offerButton');

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
  const offerAmount = parseFloat(offerInput.value);
  offerButton.disabled = true;

  // Make a POST request to place the offer for the auction
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
      // Display error messages if any
      if (responseData.error) {
        displayMessages(responseData.messages, responseData.error);
      } else {
        throw new Error('Oferta fallida');
      }
    } else {
      offerInput.value = '';
      displayMessages(responseData.messages, responseData.error);
    }
  } catch (error) {
    console.error('Error:', error);
    displayMessages(["La puja de tu oferta falló"], true);
  } finally {
    offerButton.disabled = false;
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
      minimumFractionDigits: 2,
      maximumFractionDigits: 20
    });
}
