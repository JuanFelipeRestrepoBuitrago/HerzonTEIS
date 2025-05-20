// language.js

const translations = {
  es: {
    login: "Iniciar Sesión",
    register: "Registrarse",
    gold: "Oro",
    silver: "Plata",
    home: "Inicio",
    jewels: "Joyería",
    auctions: "Subastas",
    history: "Historial",
  },
  en: {
    login: "Log In",
    register: "Register",
    gold: "Gold",
    silver: "Silver",
    home: "Home",
    jewels: "Jewels",
    auctions: "Auctions",
    history: "History",
  },
  fr: {
    login: "Connexion",
    register: "S'inscrire",
    gold: "Or",
    silver: "Argent",
    home: "Accueil",
    jewels: "Bijoux",
    auctions: "Enchères",
    history: "Historique",
  },
  it: {
    login: "Accedi",
    register: "Registrati",
    gold: "Oro",
    silver: "Argento",
    home: "Home",
    jewels: "Gioielli",
    auctions: "Aste",
    history: "Cronologia",
  },
  pt: {
    login: "Entrar",
    register: "Registrar",
    gold: "Ouro",
    silver: "Prata",
    home: "Início",
    jewels: "Joias",
    auctions: "Leilões",
    history: "Histórico",
  },
  de: {
    login: "Anmelden",
    register: "Registrieren",
    gold: "Gold",
    silver: "Silber",
    home: "Startseite",
    jewels: "Schmuck",
    auctions: "Auktionen",
    history: "Verlauf",
  },
};

/**
 * Initializes Google Translate widget with custom configuration.
 */
function googleTranslateElementInit() {
  new google.translate.TranslateElement(
    {
      pageLanguage: "en",
      includedLanguages: "es,en,fr,it,pt,de",
      autoDisplay: false,
    },
    "google_translate_element"
  );
}

/**
 * @param {string} lang
 */
function updateAllTexts(lang) {
  const texts = translations[lang] || translations.en;

  const loginText = document.querySelector(".login-text");
  const registerText = document.querySelector(".register-text");
  if (loginText) loginText.textContent = texts.login;
  if (registerText) registerText.textContent = texts.register;

  const goldPrice = document.getElementById("gold-price");
  const silverPrice = document.getElementById("silver-price");
  const mobileGoldPrice = document.getElementById("mobile-gold-price");
  const mobileSilverPrice = document.getElementById("mobile-silver-price");

  if (goldPrice) {
    const price = goldPrice.textContent.split(":")[1]?.trim() || "Loading...";
    goldPrice.textContent = `${texts.gold}: ${price}`;
  }
  if (silverPrice) {
    const price = silverPrice.textContent.split(":")[1]?.trim() || "Loading...";
    silverPrice.textContent = `${texts.silver}: ${price}`;
  }
  if (mobileGoldPrice) {
    const price =
      mobileGoldPrice.textContent.split(":")[1]?.trim() || "Loading...";
    mobileGoldPrice.textContent = `${texts.gold}: ${price}`;
  }
  if (mobileSilverPrice) {
    const price =
      mobileSilverPrice.textContent.split(":")[1]?.trim() || "Loading...";
    mobileSilverPrice.textContent = `${texts.silver}: ${price}`;
  }

  const navLinks = document.querySelectorAll(".nav-link");
  navLinks.forEach((link) => {
    const text = link.textContent.trim();
    if (text === "Home") link.textContent = texts.home;
    else if (text === "Jewels") link.textContent = texts.jewels;
    else if (text === "Auctions") link.textContent = texts.auctions;
    else if (text === "History") link.textContent = texts.history;
  });
}

/**
 * Changes the active language in Google Translate and updates UI.
 * @param {string} lang - Language code to switch to.
 */
function changeLanguage(lang) {
  localStorage.setItem("language", lang.toUpperCase());
  document.getElementById("selected-lang").textContent = lang.toUpperCase();
  updateFlag(lang);

  updateAllTexts(lang);

  const select = document.querySelector(".goog-te-combo");
  if (select) {
    select.value = lang;
    select.dispatchEvent(new Event("change"));
  }
}

/**
 * Updates the flag icon next to the language selector.
 * @param {string} lang - Language code.
 */
function updateFlag(lang) {
  const flagMap = {
    es: "es",
    en: "us",
    fr: "fr",
    it: "it",
    pt: "pt",
    de: "de",
  };

  const flagElement = document.querySelector("#langDropdown .flag-icon");
  if (flagElement && flagMap[lang]) {
    flagElement.className = `flag-icon flag-icon-${flagMap[lang]} ms-1`;
  }
}

/**
 * Applies the saved language to the UI.
 */
function applySavedLanguage() {
  const savedLanguage = localStorage.getItem("language") || "EN";
  const lang = savedLanguage.toLowerCase();

  document.getElementById("selected-lang").textContent = savedLanguage;
  updateFlag(lang);
  updateAllTexts(lang);

  // Si el idioma es inglés, no dispares Google Translate
  if (lang === "en") return;

  // Si es otro idioma, espera a que Google Translate esté listo y traduce
  const maxTries = 30;
  let tries = 0;
  const checkTranslateReady = setInterval(() => {
    const select = document.querySelector(".goog-te-combo");
    if (select) {
      select.value = lang;
      select.dispatchEvent(new Event("change", { bubbles: true }));
      clearInterval(checkTranslateReady);
    } else if (++tries > maxTries) {
      clearInterval(checkTranslateReady);
    }
  }, 150);
}

window.addEventListener("load", () => {
  if (!document.getElementById("google-translate-script")) {
    const script = document.createElement("script");
    script.id = "google-translate-script";
    script.src =
      "https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit";
    document.body.appendChild(script);
  }

  applySavedLanguage();
});
