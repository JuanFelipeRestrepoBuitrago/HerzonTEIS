$(document).ready(function () {
  // Clear filter and return to full jewels list
  $('#clearFilter').on('click', function () {
    window.location.href = '/jewels';
  });

  /**
   * Dynamically adjusts the grid layout of the jewels container
   * based on the number of visible jewel cards.
   */
  function adjustGrid() {
    const jewelCount = $('#jewels-container .col').length;
    const container = $('#jewels-container');

    container.removeClass('single-result two-results');

    if (jewelCount === 1) {
      container.addClass('single-result');
      container.css({
        'grid-template-columns': '1fr',
        'justify-content': 'center'
      });
    } else if (jewelCount === 2) {
      container.addClass('two-results');
      container.css({
        'grid-template-columns': '1fr 1fr',
        'justify-content': 'center'
      });
    } else {
      container.css({
        'grid-template-columns': 'repeat(3, 1fr)',
        'justify-content': 'center'
      });
    }
  }

  // Run grid adjustment on page load
  adjustGrid();

  // Re-adjust grid after form submission (e.g. filters)
  $('form').on('submit', function () {
    setTimeout(adjustGrid, 100);
  });
});
