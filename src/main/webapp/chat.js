// 页面加载完成后执行
function initPage() {
    // 滚动到底部
    const chatHistory = document.getElementById('chat-history');
    if (chatHistory) {
        chatHistory.scrollTop = chatHistory.scrollHeight;
    }

    // 如果允许群聊，聚焦到输入框
    const messageInput = document.getElementById('message-input');
    if (messageInput && !messageInput.disabled) {
        messageInput.focus();
    }

    // 当下拉框选择用户时，自动切换到私聊模式
    const username2Select = document.getElementById('username2');
    if (username2Select) {
        username2Select.addEventListener('change', function() {
            if (this.value) {
                document.getElementById('chatType').value = 'private';
                setTimeout(switchChatMode, 100);
            }
        });
    }
}

// 自动刷新页面（每10秒）
setTimeout(function() {
     window.location.reload();
     }, 10000);
