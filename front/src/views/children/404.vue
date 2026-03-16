<template>
  <div class="not-found">
    <div class="container">
      <div class="number">404</div>
      <h1 class="title">页面未找到</h1>
      <p class="message">
        抱歉，你访问的页面可能已被移除、更名或暂时不可用。
        <span class="emoji">😭</span>
      </p>
      <router-link to="/" class="home-link">返回首页</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
.not-found {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(145deg, #f5f7fa 0%, #e9ecf2 100%);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  text-align: center;
  padding: 20px;
  box-sizing: border-box;
}

.container {
  max-width: 600px;
  animation: fadeIn 0.6s ease-out;
}

.number {
  font-size: clamp(8rem, 25vw, 12rem);
  font-weight: 800;
  line-height: 1;
  color: #2c3e50;
  text-shadow: 8px 8px 0 rgba(0, 0, 0, 0.05);
  margin-bottom: 0.2rem;
}

.title {
  font-size: clamp(1.8rem, 5vw, 2.5rem);
  font-weight: 600;
  color: #34495e;
  margin: 0 0 1rem 0;
  letter-spacing: 1px;
}

.message {
  font-size: clamp(1rem, 3vw, 1.2rem);
  color: #5d6d7e;
  margin-bottom: 2.5rem;
  line-height: 1.6;
  padding: 0 20px;
}

.emoji {
  display: inline-block;
  margin-left: 5px;
  font-size: 1.3em;
}

.home-link {
  display: inline-block;
  padding: 14px 36px;
  background-color: #3498db;
  color: white;
  text-decoration: none;
  font-weight: 500;
  font-size: 1.1rem;
  border-radius: 50px;
  box-shadow: 0 8px 20px rgba(52, 152, 219, 0.3);
  transition: all 0.2s ease;
  border: none;
  cursor: pointer;
}

.home-link:hover {
  background-color: #2980b9;
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(52, 152, 219, 0.4);
}

.home-link:active {
  transform: translateY(0);
  box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>