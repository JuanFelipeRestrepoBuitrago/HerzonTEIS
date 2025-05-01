/**
 * @file Handles real-time bidding for auctions using WebSocket and STOMP.
 * @module auctionLive
 */

// Initialize SockJS and STOMP client for WebSocket communication
const socket = new SockJS('/ws/auction/websocket');
const stompClient = Stomp.over(socket);

// DOM elements
const auctionId = document.getElementById('auctionId').textContent;
const offerInput = document.getElementById('offerAmount');
const offerButton = document.getElementById('offerButton');

/**
 * Establishes WebSocket connection and subscribes to live auction updates.
 */
stompClient.connect({}, function (frame) {
  console.log('Connected:', frame);

  // Subscribe to auction updates
  stompClient.subscribe(`/topic/auction/updates/${auctionId}`, (response) => {
    const auctionUpdate = JSON.parse(response.body);
    updateAuctionUI(auctionUpdate);
  });
}, (error) => {
  console.error('Failed to connect to live auction service:', error);
});

/**
 * Gracefully disconnects from WebSocket when the user leaves the page.
 */
window.addEventListener('beforeunload', () => {
  if (stompClient) stompClient.disconnect();
});

/**
 * Sends a new offer for the current auction.
 * Disables the bid button during the request and handles CSRF protection.
 */
async function placeOffer() {
  const offerAmount = parseFloat(offerInput.value);
  offerButton.disabled = true;

  const csrfToken = document.querySelector("meta[name='_csrf']").content;
  const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;

  try {
    const response = await fetch('/auction/offer/place', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify({
        auctionId: parseInt(auctionId),
        offerPrice: offerAmount
      })
    });

    if (response.status === 401) {
      window.location.href = '/login';
      return;
    }

    const data = await response.json();

    if (!response.ok) {
      if (data.error) {
        displayMessages(data.messages, data.error);
      } else {
        throw new Error('Bid failed');
      }
    } else {
      offerInput.value = '';
      displayMessages(data.messages, data.error);
    }
  } catch (error) {
    console.error('Error:', error);
    displayMessages(['Your bid could not be placed'], true);
  } finally {
    offerButton.disabled = false;
  }
}

/**
 * Updates the auction UI with the latest data.
 * @param {Object} offerAuctionResponse - Auction data received from WebSocket.
 * @param {number} offerAuctionResponse.currentPrice - The current price of the auction.
 */
function updateAuctionUI(offerAuctionResponse) {
  console.log('Auction updated:', offerAuctionResponse);
  document.getElementById('current-price').innerText =
    offerAuctionResponse.currentPrice.toLocaleString('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: 20
    });
}
