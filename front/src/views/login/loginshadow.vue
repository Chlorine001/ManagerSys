<template>
  <div
    style="position: fixed;top:-100%;left:0;width:100%;height: 100%;background-color: var(--app-login-shadow);z-index: 999999;text-align: center;animation: Tops 1600ms forwards;"
    :style="{ display: 'flex', alignItems: 'center', justifyContent: 'center' }">
    <div style="position: absolute;top:-40px;left:0;width:100%;height: 40px;background-color: var(--app-login-shadow);">
    </div>
    <div style="text-align: center; margin-top: -80px;">
      <div
        style="width: 120px;height:120px;margin: 0px 30px 15px;background-color: var(--app-panel);border-radius: 40%; animation: sizeset 1000ms forwards;">
        <img :src="$root.isDark ? '/favicon-inverted.png' : '/favicon.ico'"
          style="width: 100%;height:100%;transform: scale(.8);" alt="">
      </div>
      <div
        style="padding:0; text-align: center;font-family: 楷体 ;font-size: 30px; color: white ;animation: shows 500ms forwards;">
        后台管理系统</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      load: 0,
    }
  },
  methods: {
    setdoms() {
      this.winheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
    }
  },
  mounted() {
    this.setdoms()
    window.addEventListener('resize', () => {
      this.setdoms()
    })
  }
}
</script>

<style>
@keyframes shows {
  0% {
    opacity: 0;
  }

  99.99% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

@keyframes Tops {
  0% {
    top: 0;
  }

  60% {
    top: 0;
  }

  85% {
    top: 40px;
  }

  100% {
    top: -100%;
  }
}

@keyframes sizeset {
  0% {
    opacity: 0;
    transform: scale(60);
  }

  40% {
    opacity: 0;
    transform: scale(60);
  }

  70% {
    opacity: 1;
    transform: scale(1);
  }

  85% {
    transform: scale(1) rotateY(180deg);
  }

  100% {
    transform: scale(1) rotateY(360deg);
  }
}
</style>