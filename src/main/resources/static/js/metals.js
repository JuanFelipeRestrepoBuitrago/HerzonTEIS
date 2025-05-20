/**
 * Fetches current gold and silver prices and updates the UI.
 * Caches values in sessionStorage to avoid unnecessary API calls.
 */
async function fetchMetalPrices() {
  const cachedGoldPrice = sessionStorage.getItem("goldPrice");
  const cachedSilverPrice = sessionStorage.getItem("silverPrice");

  const currentLang = localStorage.getItem("language")?.toLowerCase() || "en";
  const translations = {
    en: { gold: "Gold", silver: "Silver" },
    es: { gold: "Oro", silver: "Plata" },
    fr: { gold: "Or", silver: "Argent" },
    it: { gold: "Oro", silver: "Argento" },
    pt: { gold: "Ouro", silver: "Prata" },
    de: { gold: "Gold", silver: "Silber" },
  };

  const texts = translations[currentLang] || translations.en;

  if (cachedGoldPrice && cachedSilverPrice) {
    // Desktop
    document.getElementById("gold-price").textContent = `${
      texts.gold
    }: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById("silver-price").textContent = `${
      texts.silver
    }: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;

    // Mobile
    document.getElementById("mobile-gold-price").textContent = `${
      texts.gold
    }: $${parseFloat(cachedGoldPrice).toFixed(2)}/oz`;
    document.getElementById("mobile-silver-price").textContent = `${
      texts.silver
    }: $${parseFloat(cachedSilverPrice).toFixed(2)}/oz`;
    return;
  }

  try {
    const [goldResponse, silverResponse] = await Promise.all([
      fetch("/api/metals/prices?metal=XAU"),
      fetch("/api/metals/prices?metal=XAG"),
    ]);

    const goldData = await goldResponse.json();
    const silverData = await silverResponse.json();

    sessionStorage.setItem("goldPrice", goldData.price);
    sessionStorage.setItem("silverPrice", silverData.price);

    // Desktop
    document.getElementById("gold-price").textContent = `${
      texts.gold
    }: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById("silver-price").textContent = `${
      texts.silver
    }: $${silverData.price.toFixed(2)}/oz`;

    // Mobile
    document.getElementById("mobile-gold-price").textContent = `${
      texts.gold
    }: $${goldData.price.toFixed(2)}/oz`;
    document.getElementById("mobile-silver-price").textContent = `${
      texts.silver
    }: $${silverData.price.toFixed(2)}/oz`;
  } catch (error) {
    console.error("Error fetching metal prices:", error);

    // Desktop
    document.getElementById(
      "gold-price"
    ).textContent = `${texts.gold}: Unavailable`;
    document.getElementById(
      "silver-price"
    ).textContent = `${texts.silver}: Unavailable`;

    // Mobile
    document.getElementById(
      "mobile-gold-price"
    ).textContent = `${texts.gold}: Unavailable`;
    document.getElementById(
      "mobile-silver-price"
    ).textContent = `${texts.silver}: Unavailable`;
  }
}

// Initialize on page load and set periodic updates
document.addEventListener("DOMContentLoaded", () => {
  fetchMetalPrices();

  const intervalId = setInterval(fetchMetalPrices, 300000); // Every 5 minutes

  window.addEventListener("storage", (event) => {
    if (event.key === "language") {
      fetchMetalPrices();
    }
  });

  window.addEventListener("beforeunload", () => {
    clearInterval(intervalId);
  });
});
