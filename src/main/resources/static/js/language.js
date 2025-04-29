function googleTranslateElementInit() {
  new google.translate.TranslateElement({
    pageLanguage: 'es',
    includedLanguages: 'es,en,fr,it,pt,de',
    autoDisplay: false
  }, 'google_translate_element');
}

function changeLanguage(lang) {
  const select = document.querySelector('.goog-te-combo');
  if (select) {
    select.value = lang;
    select.dispatchEvent(new Event('change'));
  }
  localStorage.setItem('language', lang.toUpperCase());
  document.getElementById('selected-lang').textContent = lang.toUpperCase();
}

window.addEventListener('load', () => {
  if (!document.getElementById('google-translate-script')) {
    const script = document.createElement('script');
    script.id = 'google-translate-script';
    script.src = 'https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit';
    document.body.appendChild(script);
  }

  const savedLanguage = localStorage.getItem('language') || 'ES';
  document.getElementById('selected-lang').textContent = savedLanguage;

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
