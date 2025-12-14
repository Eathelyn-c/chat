// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
   // 创建用户切换表单
   createUserSwitchForm();
});

// 创建用户切换表单
function createUserSwitchForm() {
   const userSelector = document.getElementById('user-selector');
   const chatForm = document.getElementById('chat-form');

   // 如果元素不存在，则退出
   if (!userSelector || !chatForm) {
       return;
   }

   // 如果已经存在用户切换表单，则不再创建
   if (document.querySelector('.user-switch-form')) {
       return;
   }

   // 创建新的表单用于切换用户
   const switchForm = document.createElement('form');
   switchForm.method = 'get';
   switchForm.action = 'chat.jsp';
   switchForm.className = 'user-switch-form';

   // 复制用户选择框
   const newUserSelector = userSelector.cloneNode(true);
   newUserSelector.id = 'user-switch-selector';

   // 添加样式
   switchForm.style.marginBottom = '15px';
   switchForm.style.padding = '10px';
   switchForm.style.border = '1px solid #ddd';
   switchForm.style.backgroundColor = '#f9f9f9';

   // 添加标题
   const title = document.createElement('h3');
   title.textContent = '请选择用户:';
   switchForm.appendChild(title);

   // 添加选择框
   switchForm.appendChild(newUserSelector);

   // 在聊天表单前插入新表单
   chatForm.parentNode.insertBefore(switchForm, chatForm);

   // 添加事件监听 - 当选择改变时提交表单
   newUserSelector.addEventListener('change', function() {
       if (this.value) {
           this.form.submit();
       }
   });

   // 隐藏原始的用户选择框
   const originalLabel = chatForm.querySelector('h2');
   if (originalLabel && originalLabel.textContent === '选择用户:') {
       originalLabel.style.display = 'none';
   }
   userSelector.style.display = 'none';
}
