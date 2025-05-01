async function fetchMetalPrices() {
   try {
     const goldResponse = await fetch("/api/metals/prices?metal=XAU");
     const goldData = await goldResponse.json();
 
     const silverResponse = await fetch("/api/metals/prices?metal=XAG");
     const silverData = await silverResponse.json();
 
     // Desktop
     document.getElementById("gold-price").textContent = `Oro: $${goldData.price.toFixed(2)}/oz`;
     document.getElementById("silver-price").textContent = `Plata: $${silverData.price.toFixed(2)}/oz`;
 
     // Móvil
     document.getElementById("mobile-gold-price").textContent = `Oro: $${goldData.price.toFixed(2)}/oz`;
     document.getElementById("mobile-silver-price").textContent = `Plata: $${silverData.price.toFixed(2)}/oz`;
   } catch (error) {
     console.error("Error fetching metal prices:", error);
     document.getElementById("gold-price").textContent = "Oro: No disponible";
     document.getElementById("silver-price").textContent = "Plata: No disponible";
     document.getElementById("mobile-gold-price").textContent = "Oro: No disponible";
     document.getElementById("mobile-silver-price").textContent = "Plata: No disponible";
   }
 }
 
 document.addEventListener("DOMContentLoaded", fetchMetalPrices);
 setInterval(fetchMetalPrices, 300000); // Cada 5 minutos
 