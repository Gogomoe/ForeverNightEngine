package moe.gogo.game.view

import javax.swing.JFrame
import javax.swing.UIManager

/**
 * 窗体工厂会在窗体初始化后设置一些基本内容
 */
abstract class WindowFactory {

    /**
     * 构建游戏窗体
     * @return 构建的窗口
     */
    abstract fun build(): Window

    /**
     * 初始化窗体主题
     */
    open fun initLookAndFeel() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    }

    /**
     * 设置基本的窗体监听器，如关闭窗体的操作
     * @param window 需要设置的窗口
     */
    open fun addWindowListener(window: Window) {
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

}

/**
 * 默认的窗体工厂实现，创建时会构建一个游戏面板
 */
class WindowFactoryImpl(val panelFactory: PanelFactory = PanelFactoryImpl()) : WindowFactory() {

    override fun build(): Window {
        val panel: Panel = panelFactory.build()
        val window: Window = createWindow(panel)

        initGamePanel(window, panel)
        initLookAndFeel()
        addWindowListener(window)

        return window
    }

    private fun createWindow(panel: Panel): Window = object : Window() {
        override val panel: Panel = panel
    }

    private fun initGamePanel(frame: Window, panel: Panel) {
        panel.setBounds(0, 0, frame.width, frame.height)
        frame.add(panel)
    }


}