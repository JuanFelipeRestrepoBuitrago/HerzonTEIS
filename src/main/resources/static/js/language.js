// language.js

/**
 * Initializes Google Translate widget with custom configuration.
 */
function googleTranslateElementInit() {
   new google.translate.TranslateElement({
     pageLanguage: 'es',
     includedLanguages: 'es,en,fr,it,pt,de',
     autoDisplay: false
   }, 'google_translate_element');
 }
 
 /**
  * Changes the active language in Google Translate and updates UI.
  * @param {string} lang - Language code to switch to.
  */
 function changeLanguage(lang) {
   const select = document.querySelector('.goog-te-combo');
   if (select) {
     select.value = lang;
     select.dispatchEvent(new Event('change'));
   }
 
   localStorage.setItem('language', lang.toUpperCase());
   document.getElementById('selected-lang').textContent = lang.toUpperCase();
 
   updateFlag(lang);
 }
 
 /**
  * Updates the flag icon next to the language selector.
  * @param {string} lang - Language code.
  */
 function updateFlag(lang) {
   const flagMap = {
     'es': 'es',
     'en': 'us',
     'fr': 'fr',
     'it': 'it',
     'pt': 'pt',
     'de': 'de'
   };
 
   const flagElement = document.querySelector('#langDropdown .flag-icon');
   if (flagElement && flagMap[lang]) {
     flagElement.className = `flag-icon flag-icon-${flagMap[lang]} ms-1`;
   }
 }
 
 // Load translation script and apply saved preferences
 window.addEventListener('load', () => {
   if (!document.getElementById('google-translate-script')) {
     const script = document.createElement('script');
     script.id = 'google-translate-script';
     script.src = 'https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit';
     document.body.appendChild(script);
   }
 
   const savedLanguage = localStorage.getItem('language') || 'EN';
   document.getElementById('selected-lang').textContent = savedLanguage;
   updateFlag(savedLanguage.toLowerCase());
 
   const observer = new MutationObserver(() => {
     const select = document.querySelector('.goog-te-combo');
     if (select) {
       select.value = savedLanguage.toLowerCase();
       select.dispatchEvent(new Event('change'));
       observer.disconnect();
     }
   });
 
   observer.observe(document.body, { childList: true, subtree: true });
 });
 