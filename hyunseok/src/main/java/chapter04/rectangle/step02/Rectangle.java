package chapter04.rectangle.step02;

public class Rectangle {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Rectangle(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    // 객체의 상태를 객체 스스로가 증가하게 한다
    public void enlarge(int multiple) {
        right *= multiple;
        bottom *= multiple;
    }

    // 현재 사각형의 길이를 알기 위한 get 메서드는 남긴다
    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }
}
