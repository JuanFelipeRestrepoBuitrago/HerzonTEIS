/**
 * Fetches current gold and silver prices and updates the UI.
 * Caches values in sessionStorage to avoid unnecessary API calls.
 */
async function fetchMetalPrices() {
  const cachedGoldPrice = sessionStorage.getItem('goldPrice');
  const cachedSilverPrice = sessionStorage.getItem('silverPrice');

  if (cachedGoldPrice && cachedSilverPrice) {
    // Desktop
    document.getElementById('gold-price').textContent = `Gold: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById('silver-price').textContent = `Silver: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;

    // Mobile
    document.getElementById('mobile-gold-price').textContent = `Gold: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById('mobile-silver-price').textContent = `Silver: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;
    return;
  }

  try {
    const [goldResponse, silverResponse] = await Promise.all([
      fetch('/api/metals/prices?metal=XAU'),
      fetch('/api/metals/prices?metal=XAG')
    ]);

    const goldData = await goldResponse.json();
    const silverData = await silverResponse.json();

    sessionStorage.setItem('goldPrice', goldData.price);
    sessionStorage.setItem('silverPrice', silverData.price);

    // Desktop
    document.getElementById('gold-price').textContent = `Gold: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById('silver-price').textContent = `Silver: $${silverData.price.toFixed(2)}/oz`;

    // Mobile
    document.getElementById('mobile-gold-price').textContent = `Gold: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById('mobile-silver-price').textContent = `Silver: $${silverData.price.toFixed(2)}/oz`;
  } catch (error) {
    console.error('Error fetching metal prices:', error);

    // Desktop
    document.getElementById('gold-price').textContent = 'Gold: Unavailable';
    document.getElementById('silver-price').textContent = 'Silver: Unavailable';

    // Mobile
    document.getElementById('mobile-gold-price').textContent = 'Gold: Unavailable';
    document.getElementById('mobile-silver-price').textContent = 'Silver: Unavailable';
  }
}

// Initialize on page load and set periodic updates
document.addEventListener('DOMContentLoaded', () => {
  fetchMetalPrices();

  const intervalId = setInterval(fetchMetalPrices, 300000); // Every 5 minutes

  window.addEventListener('beforeunload', () => {
    clearInterval(intervalId);
  });
});
