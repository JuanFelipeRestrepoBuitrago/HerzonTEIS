async function fetchMetalPrices() {
  // Check if prices are already cached in sessionStorage
  const cachedGoldPrice = sessionStorage.getItem('goldPrice');
  const cachedSilverPrice = sessionStorage.getItem('silverPrice');

  // If cached data exists, use it instead of fetching
  if (cachedGoldPrice && cachedSilverPrice) {
    // Desktop
    document.getElementById('gold-price').textContent = `Oro: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById('silver-price').textContent = `Plata: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;

    // Mobile
    document.getElementById('mobile-gold-price').textContent = `Oro: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById('mobile-silver-price').textContent = `Plata: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;
    return; // Exit the function to avoid API call
  }

  try {
    // Fetch gold price
    const goldResponse = await fetch('/api/metals/prices?metal=XAU');
    const goldData = await goldResponse.json();

    // Fetch silver price
    const silverResponse = await fetch('/api/metals/prices?metal=XAG');
    const silverData = await silverResponse.json();

    // Cache the prices in sessionStorage
    sessionStorage.setItem('goldPrice', goldData.price);
    sessionStorage.setItem('silverPrice', silverData.price);

    // Desktop
    document.getElementById('gold-price').textContent = `Oro: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById('silver-price').textContent = `Plata: $${silverData.price.toFixed(2)}/oz`;

    // Mobile
    document.getElementById('mobile-gold-price').textContent = `Oro: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById('mobile-silver-price').textContent = `Plata: $${silverData.price.toFixed(2)}/oz`;
  } catch (error) {
    console.error('Error fetching metal prices:', error);
    // Update UI with error message
    document.getElementById('gold-price').textContent = 'Oro: No disponible';
    document.getElementById('silver-price').textContent = 'Plata: No disponible';
    document.getElementById('mobile-gold-price').textContent = 'Oro: No disponible';
    document.getElementById('mobile-silver-price').textContent = 'Plata: No disponible';
  }
}

// Run on page load
document.addEventListener('DOMContentLoaded', () => {
  fetchMetalPrices();
  // Set interval for periodic updates (every 5 minutes)
  const intervalId = setInterval(fetchMetalPrices, 300000);

  // Clear interval when the page is unloaded to prevent memory leaks
  window.addEventListener('beforeunload', () => {
    clearInterval(intervalId);
  });
});