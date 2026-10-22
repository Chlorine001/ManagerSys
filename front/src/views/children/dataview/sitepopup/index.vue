<template>
  <div>
    <div style="margin-bottom: 20px;padding:10px; background-color: var(--app-panel);color: var(--app-text-secondary);"
      :style="{ boxShadow: $root.boxShadow }">
      ⚠️ 注意：调整窗口大小和左右伸缩左侧导航将导致下方区域内容重置！
    </div>
    <div style="margin-bottom: 20px;padding:10px; background-color: var(--app-panel);"
      :style="{ boxShadow: $root.boxShadow }">
      <section class="signature">
        <div class="signatureBox" ref="boxRef">
          <div class="canvasBox" ref="canvasHW">
            <canvas ref="canvasF" @touchstart.passive='touchStart' @touchmove.passive='touchMove'
              @touchend.passive='touchEnd' @mousedown="mouseDown" @mousemove="mouseMove" @mouseup="mouseUp"></canvas>
            <div class="btnBox">
              <el-button style="margin:0 10px 10px 0" @click="overwrite">重写</el-button>
              <!-- <el-button style="margin:0 10px 10px 0" @click="commit">提交签名</el-button> -->
              <el-button style="margin:0 10px 10px 0" @click="preview">预览</el-button>
            </div>
          </div>
        </div>
        <!-- <img v-if="imgUrl" class="imgCanvas" :src="imgUrl" alt="签名预览"> -->
      </section>
    </div>

    <!-- 预览签名弹窗 -->
    <el-dialog v-model="dialogVisible" title="签名预览" width="90%" center>
      <div style="text-align: center; background:var(--app-panel); padding: 20px;">
        <img :src="imgUrl" style="max-width: 100%; max-height: 100%; border-radius: 8px; background: var(--app-panel);"
          alt="签名预览">
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="info" @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="commitFromDialog">提 交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox, ElLoading } from 'element-plus'
export default {
  data() {
    return {
      stageInfo: '',
      imgUrl: '',
      client: {},
      points: [],
      canvasTxt: null,
      startX: 0,
      startY: 0,
      moveY: 0,
      moveX: 0,
      endY: 0,
      endX: 0,
      w: null,
      h: null,
      isDown: false,
      isViewAutograph: this.$route.query.isViews > 0,
      contractSuccess: this.$route.query.contractSuccess,
      dialogVisible: false, // 控制弹窗显示
    }
  },
  watch: {
    '$root.openchange': function (news, olds) {
      setTimeout(() => {
        this.initCanvas()
      }, 300);
    }
  },
  mounted() {
    this.initCanvas()
    window.addEventListener('resize', this.initCanvas)
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.initCanvas)
  },
  methods: {
    initCanvas() {
      let canvas = this.$refs.canvasF
      let box = this.$refs.canvasHW
      if (!canvas || !box) return

      // 用更安全的算法获取高度，防止变成负数
      let wrapper = this.$refs.boxRef // 拿到最外层有固定高度的容器
      let btnBox = box.querySelector('.btnBox')
      let btnHeight = btnBox ? btnBox.offsetHeight : 80
      let height = wrapper.offsetHeight - btnHeight - 10 // 用最外层高度减去真实按钮高度
      // let height = box.offsetHeight - 80 // 减去按钮和边框的粗略高度
      if (height < 200) height = 300     // 兜底高度

      let width = box.offsetWidth

      // 解决高分屏 (Retina) 线条模糊问题
      let dpr = window.devicePixelRatio || 1

      // 重置宽高会自动清空画布
      canvas.width = width * dpr
      canvas.height = height * dpr
      canvas.style.width = width + 'px'
      canvas.style.height = height + 'px'

      this.canvasTxt = canvas.getContext('2d')
      this.canvasTxt.scale(dpr, dpr) // 放大坐标系

      // 设置画笔样式（解决暗黑模式看不见线条的问题）
      this.canvasTxt.lineWidth = 2
      this.canvasTxt.lineCap = 'round'
      this.canvasTxt.lineJoin = 'round'
      // let isDark = document.documentElement.classList.contains('dark')
      // this.canvasTxt.strokeStyle = isDark ? '#ffffff' : '#000000'

      // 顺便清空数据
      this.points = []
      this.imgUrl = ''

      // 更新 canvas 相对视口的位置
      this.stageInfo = canvas.getBoundingClientRect()
    },

    //========= 触控 / 鼠标事件 =========
    //=========mobile=========
    touchStart(ev) {
      ev.preventDefault()
      if (ev.touches.length == 1) {
        let obj = {
          x: ev.targetTouches[0].clientX - this.stageInfo.left,
          y: ev.targetTouches[0].clientY - this.stageInfo.top,
        }
        this.startX = obj.x
        this.startY = obj.y
        this.canvasTxt.beginPath()
        this.canvasTxt.moveTo(this.startX, this.startY)
        this.canvasTxt.lineTo(obj.x, obj.y)
        this.canvasTxt.stroke()
        this.canvasTxt.closePath()
        this.points.push(obj)
      }
    },
    touchMove(ev) {
      ev.preventDefault()
      if (ev.touches.length == 1) {
        this.canvasTxt.strokeStyle = getComputedStyle(document.documentElement).getPropertyValue('--app-text').trim();
        let obj = {
          x: ev.targetTouches[0].clientX - this.stageInfo.left,
          y: ev.targetTouches[0].clientY - this.stageInfo.top
        }
        this.moveY = obj.y
        this.moveX = obj.x
        this.canvasTxt.beginPath()
        this.canvasTxt.moveTo(this.startX, this.startY)
        this.canvasTxt.lineTo(obj.x, obj.y)
        this.canvasTxt.stroke()
        this.canvasTxt.closePath()
        this.startY = obj.y
        this.startX = obj.x
        this.points.push(obj)
      }
    },
    touchEnd(ev) {
      ev.preventDefault()
      // 手指抬起，直接完事，不需要再画了
    },

    //=======pc========
    mouseDown(ev) {
      ev.preventDefault()
      this.canvasTxt.strokeStyle = getComputedStyle(document.documentElement).getPropertyValue('--app-text').trim();
      let obj = {
        x: ev.offsetX,
        y: ev.offsetY
      }
      this.startX = obj.x
      this.startY = obj.y
      this.canvasTxt.beginPath()
      this.canvasTxt.moveTo(this.startX, this.startY)
      this.canvasTxt.lineTo(obj.x, obj.y)
      this.canvasTxt.stroke()
      this.canvasTxt.closePath()
      this.points.push(obj)
      this.isDown = true
    },
    mouseMove(ev) {
      ev.preventDefault()
      if (this.isDown) {
        let obj = {
          x: ev.offsetX,
          y: ev.offsetY
        }
        this.moveY = obj.y
        this.moveX = obj.x
        this.canvasTxt.beginPath()
        this.canvasTxt.moveTo(this.startX, this.startY)
        this.canvasTxt.lineTo(obj.x, obj.y)
        this.canvasTxt.stroke()
        this.canvasTxt.closePath()
        this.startY = obj.y
        this.startX = obj.x
        this.points.push(obj)
      }
    },
    mouseUp(ev) {
      ev.preventDefault()
      this.isDown = false
    },

    //=======function========
    //重写
    overwrite() {
      this.initCanvas() // 直接调用初始化，完美清空
    },
    // 预览按钮
    preview() {
      if (this.points.length === 0) {
        ElMessage.warning('请先手写签名')
        return
      }
      this.imgUrl = this.$refs.canvasF.toDataURL();
      this.dialogVisible = true
    },
    // 提交按钮（在弹窗里）
    commitFromDialog() {
      this.dialogVisible = false
      this.commit()
    },
    //提交签名
    // 直接提交按钮（在画板下方，如果想保留的话）
    commit() {
      if (this.points.length === 0) {
        ElMessage.warning('请先手写签名')
        return
      }
      // console.log('签名Base64:', imgUrl)
      ElMessage.success('签名提交成功！')

      // 如果需要提交给后台，在这里发请求：
      // axios.post('/api/signature', { img: imgUrl })
    }
  }
}
</script>

<style scoped>
.signatureBox {
  width: 100%;
  height: 400px;
  /* height: calc(100% - 50px); */
  box-sizing: border-box;
  overflow: hidden;
  background: var(--app-panel);
  z-index: 100;
  display: flex;
  flex-direction: column;
}

.canvasBox {
  box-sizing: border-box;
}

canvas {
  /* 1. 改边框颜色变量 */
  border: 1px solid var(--app-border);
  /* 2. 关键：让宽高包含边框，不再溢出 */
  box-sizing: border-box;
  /* 3. 关键：消除 canvas 默认的 inline-block 带来的底部多余留白 */
  display: block;
  /* 4. 禁止移动端默认行为 */
  touch-action: none;
}

.btnBox {
  padding: 10px;
  text-align: center;
}

.btnBox button:first-of-type {
  background: transparent;
  border-radius: 4px;
  height: 40px;
  width: 80px;
  font-size: 14px;
}

.btnBox button:last-of-type {
  background: #71b900;
  color: #fff;
  border-radius: 4px;
  height: 40px;
  width: 80px;
  font-size: 14px;
}
</style>