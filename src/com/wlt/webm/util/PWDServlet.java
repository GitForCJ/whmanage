package com.wlt.webm.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.awt.*; //�����û��ӿڡ���ͼ��ͼ���������
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class PWDServlet extends HttpServlet {

    /**
     * ������֤��ͼƬ
     * @param request �ͻ����������
     * @param response ��Ӧ����
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding("gbk");
        //������������ݵĸ�ʽΪͼ�����ͣ�����Ϊjpeg��ʽ
        response.setContentType("image/jpeg");
        //����ҳ�治����
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // ���ڴ��д���ͼ��
        int width = 120, height = 120;
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
//        g.setColor(new Color());
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
        int numx=1;
        int numy=1;
        for (int i = 0; i <9; i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
            // ����֤����ʾ��ͼ����
            g.setColor(new Color(20 + random.nextInt(110),
                                 20 + random.nextInt(110),
                                 20 + random.nextInt(110))); //���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
            if((i+1)%3!=0){
            	numx++;
            	g.drawString(rand, 24 * numx + 6, 26*numy);
            }else{
            	numx=1;
            	g.drawString(rand, 24 * numx + 6, 26*numy);
            	numy++;
            	
            }
        }
       

        // ����֤�����SESSION
        HttpSession session = request.getSession();
        session.setAttribute("code", code);

        // ͼ����Ч
        g.dispose();

        // ���ͼ��ҳ��
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * ������Χ��������ɫ
     * @param fc ��ʼֵ
     * @param bc ��ֵֹ
     * @return ��ɫ
     */
    private Color getRandColor(int fc, int bc) { //������Χ��������ɫ
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
