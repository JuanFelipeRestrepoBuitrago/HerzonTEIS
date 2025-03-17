/**
 * @file Manages error and success messages displayed on the UI.
 * @module messages
 */

const messagesContainer = document.getElementById('messagesAlert');

/**
 * Displays error or success messages on the UI
 * @function
 * @param {Array} messages - Array of messages to display
 * @param {boolean} error - Flag to indicate if messages are errors
 */
function displayMessages(messages, error) {
  messagesContainer.innerHTML = ''; // Clear any existing messages

  messages.forEach(message => {
    const alertClass = error ? 'alert-danger' : 'alert-success';
    const iconClass = error ? 'bi-exclamation-circle-fill' : 'bi-check-circle-fill';

    const alert = document.createElement('div');
    alert.className = `alert ${alertClass} alert-dismissible fade show mb-2 shadow-lg text-center p-3 w-100`;
    alert.innerHTML = `
      <div class="d-flex align-items-center justify-content-center">
        <i class="bi ${iconClass} me-2"></i>
        <strong>${message}</strong>
      </div>
      <button type="button" class="btn-close" 
        data-bs-dismiss="alert" aria-label="Close"
        onclick="dismissMessage(this)">
      </button>
    `;

    messagesContainer.appendChild(alert);
  });

  // Make sure the message container is visible
  messagesContainer.classList.remove('d-none');
  messagesContainer.classList.add('d-flex', 'flex-column', 'align-items-center');
}

/**
 * Dismisses a message from the UI
 * @function
 * @param {Element} button - Button element that triggered the dismiss action
 */
function dismissMessage(button) {
  // Remove the alert
  const messageAlert = button.closest('.alert');
  messageAlert.remove();

  // Hide the message container if there are no more messages
  if (messagesContainer.children.length === 0) {
    messagesContainer.classList.add('d-none');
    messagesContainer.classList.remove('d-flex');
  }
}
