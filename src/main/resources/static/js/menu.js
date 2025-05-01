/**
 * Toggles the navigation menu when the hamburger icon is clicked.
 */
document.addEventListener('DOMContentLoaded', () => {
    const hamburger = document.querySelector('.hamburger');
    const nav = document.querySelector('header nav');
  
    if (hamburger && nav) {
      hamburger.addEventListener('click', () => {
        nav.classList.toggle('active');
      });
    }
  });
  