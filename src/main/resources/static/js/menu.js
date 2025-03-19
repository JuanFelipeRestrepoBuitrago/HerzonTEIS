document.addEventListener('DOMContentLoaded', function() {
   document.querySelector('.hamburger').addEventListener('click', function() {
       document.querySelector('header nav').classList.toggle('active');
   });
});