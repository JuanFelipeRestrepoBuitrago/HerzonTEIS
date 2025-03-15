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
const auctionId = document.getElementById('auctionId').value;

/**
 * Initializes WebSocket connection and subscribes to auction updates
 * @function
 * @listens Stomp#connect
 */
stompClient.connect({}, function(frame) {
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
        auctionId: auctionId,
        offerAmount: offerAmount
      })
    });

    if (!response.ok) throw new Error('Offer failed');
    offerInput.value = '';
  } catch (error) {
    console.error('Error:', error);
    alert("Failed to place offer");
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
      currency: 'USD'
    });
}