document.addEventListener('DOMContentLoaded', () => {
  const messagesContainer = document.getElementById('messagesAlert');
  
  if (messagesContainer) {
    const messages = messagesContainer.dataset.messages?.split('||') || [];
    const isError = messagesContainer.dataset.error === 'true';
    
    if (messages.length > 0) {
      displayMessages(messages, isError);
    }
  }
});