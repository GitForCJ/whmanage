package com.wlt.webm.util;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import java.awt.*; //�����û��ӿڡ���ͼ��ͼ���������

import javax.imageio.*;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


import java.awt.image.BufferedImage;

/**
 * ��֤��������<br>
 */
public class CodeServlet extends HttpServlet {
    /**
     * ������֤��ͼƬ
     * @param request �ͻ����������
     * @param response ��Ӧ����
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        //������������ݵĸ�ʽΪͼ�����ͣ�����Ϊjpeg��ʽ
        response.setContentType("image/jpeg");
        //����ҳ�治����
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // ���ڴ��д���ͼ��
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height,
                                                BufferedImage.TYPE_INT_RGB);
        
        // ��ȡͼ��������
        Graphics g = image.getGraphics();
        //���������
        Random random = new Random();
        // �趨����ɫ
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        //�趨����
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //���߿�
        //g.setColor(new Color());
        g.drawRect(0, 0, width - 1, height - 1);

        // �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // ȡ�����������֤��(4λ����)
        String code = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
            // ����֤����ʾ��ͼ����
            g.setColor(new Color(20 + random.nextInt(110),
                                 20 + random.nextInt(110),
                                 20 + random.nextInt(110))); //���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
            g.drawString(rand, 13 * i + 6, 16);
        }

        // ����֤�����SESSION
        HttpSession session = request.getSession();
        session.setAttribute("extracodeSession", code);

        // ͼ����Ч
        g.dispose();

        // ���ͼ��ҳ��
       //ImageIO.write(image, "JPEG", response.getOutputStream());
        // ��ͼ��������ͻ���   
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());   
        encoder.encode(image);
    }

    /**
     * ������Χ��������ɫ
     * @param fc ��ʼֵ
     * @param bc ��ֵֹ
     * @return ��ɫ
     */
    private Color getRandColor(int fc, int bc) {
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
     * ������֤��ͼƬ
     * @param request �ͻ����������
     * @param response ��Ӧ����
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}
