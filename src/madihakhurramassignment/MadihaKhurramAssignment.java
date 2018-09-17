package madihakhurramassignment;

/**
 * @author aftab
 */

 public class MadihaKhurramAssignment
{
     static MadihaKhurramAssignment MAIN;
     static FrameOne fo ;
     static FrameTwo ft;
     static LastFrame lf;
     static RseoverRventFrame rf;
     
     public MadihaKhurramAssignment ()
    {
         rf = new  RseoverRventFrame();
         fo = new FrameOne();
         ft = new FrameTwo();
         lf = new LastFrame(); 
         fo.setVisible(true);
    }
        
     public static void main(String[] args)
    {
         MAIN = new MadihaKhurramAssignment();
    }
}