package com.wlt.webm.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.awt.*; //创建用户接口、绘图和图像的所有类
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class PWDServlet extends HttpServlet {

    /**
     * 生成验证码图片
     * @param request 客户端请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding("gbk");
        //设置输出的内容的格式为图象类型，并且为jpeg格式
        response.setContentType("image/jpeg");
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 120, height = 120;
        BufferedImage image = new BufferedImage(width, height,
                                                BufferedImage.TYPE_INT_RGB);
        
        // 获取图形上下文
        Graphics g = image.getGraphics();
        //生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //画边框
//        g.setColor(new Color());
        g.drawRect(0, 0, width - 1, height - 1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String code = "";
        int numx=1;
        int numy=1;
        for (int i = 0; i <9; i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110),
                                 20 + random.nextInt(110),
                                 20 + random.nextInt(110))); //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            if((i+1)%3!=0){
            	numx++;
            	g.drawString(rand, 24 * numx + 6, 26*numy);
            }else{
            	numx=1;
            	g.drawString(rand, 24 * numx + 6, 26*numy);
            	numy++;
            	
            }
        }
       

        // 将认证码存入SESSION
        HttpSession session = request.getSession();
        session.setAttribute("code", code);

        // 图象生效
        g.dispose();

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * 给定范围获得随机颜色
     * @param fc 起始值
     * @param bc 终止值
     * @return 颜色
     */
    private Color getRandColor(int fc, int bc) { //给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 生成验证码图片
     * @param request 客户端请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

}
