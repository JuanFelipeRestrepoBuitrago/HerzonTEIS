/**
 * @file Handles error and success message display logic for the UI.
 * @module messages
 */

const messagesContainer = document.getElementById('messagesAlert');

/**
 * Displays success or error messages in the alert container.
 * @param {Array<string>} messages - List of messages to display.
 * @param {boolean} error - If true, renders as error messages; otherwise, success.
 */
function displayMessages(messages, error) {
  messagesContainer.innerHTML = ''; // Clear previous alerts

  messages.forEach(message => {
    const alertClass = error ? 'alert-danger' : 'alert-success';
    const iconClass = error ? 'fa-exclamation-circle' : 'fa-check-circle';
    const closeBtnClass = error ? 'btn-close-white' : ''; // Optional: white close icon for error background

    const alert = document.createElement('div');
    alert.className = `alert ${alertClass} alert-dismissible fade show mb-2 shadow-lg text-center p-3 w-100`;

    if (error) {
      alert.style.backgroundColor = 'red'; // Custom red background for visibility
    }

    alert.innerHTML = `
      <div class="d-flex align-items-center justify-content-center">
        <i class="fas ${iconClass} me-2"></i>
        <strong>${message}</strong>
      </div>
      <button type="button" class="btn-close ${closeBtnClass}" 
              data-bs-dismiss="alert" aria-label="Close"
              onclick="dismissMessage(this)">
      </button>
    `;

    messagesContainer.appendChild(alert);
  });

  messagesContainer.classList.remove('d-none');
  messagesContainer.classList.add('d-flex', 'flex-column', 'align-items-center');
}

/**
 * Removes a specific alert from the DOM.
 * @param {HTMLElement} button - The close button that triggered the dismiss.
 */
function dismissMessage(button) {
  const alert = button.closest('.alert');
  if (alert) {
    alert.remove();
  }

  if (messagesContainer.children.length === 0) {
    messagesContainer.classList.add('d-none');
    messagesContainer.classList.remove('d-flex');
  }
}
