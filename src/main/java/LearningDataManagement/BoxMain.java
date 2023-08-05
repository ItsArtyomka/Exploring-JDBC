package LearningDataManagement;

public class BoxMain {
    private static Dao boxdao;
    public static void main(String[] args) {
        boxdao = new BoxDao();
        boxdao.save(new Box("TestNameBox"));
    }
}
