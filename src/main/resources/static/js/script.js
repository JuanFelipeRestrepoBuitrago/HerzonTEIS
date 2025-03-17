/**
 * Handles the mobile menu toggle functionality.
 * Toggles the 'active' class on the nav menu when the menu toggle button is clicked.
 */
document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('.nav-menu');

    if (menuToggle && navMenu) {
        menuToggle.addEventListener('click', function () {
            navMenu.classList.toggle('active');
        });
    }
});