$(document).ready(function () {
  $("#clearFilter").on("click", function () {
    window.location.href = "/jewels";
  });

  function adjustGrid() {
    const jewelCount = $("#jewels-container .col").length;
    const container = $("#jewels-container");

    container.removeClass("single-result two-results");

    if (jewelCount === 1) {
      container.addClass("single-result");
      container.css("grid-template-columns", "1fr"); 
      container.css("justify-content", "center");
    } else if (jewelCount === 2) {
      container.addClass("two-results");
      container.css("grid-template-columns", "1fr 1fr");
      container.css("justify-content", "center");
    } else {
      container.css("grid-template-columns", "repeat(3, 1fr)"); 
      container.css("justify-content", "center");
    }
  }

  adjustGrid();
  $("form").on("submit", function () {
    setTimeout(adjustGrid, 100);
  });
});