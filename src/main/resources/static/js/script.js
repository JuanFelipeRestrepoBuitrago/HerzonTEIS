/**
 * Toggles the visibility of the mobile navigation menu.
 * Adds or removes the 'active' class from the menu when the toggle button is clicked.
 */
document.addEventListener('DOMContentLoaded', () => {
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('.nav-menu');
  
    if (menuToggle && navMenu) {
      menuToggle.addEventListener('click', () => {
        navMenu.classList.toggle('active');
      });
    }
  });
  