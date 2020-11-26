package tsp;

import java.util.ArrayList;
import java.util.List;

/**
 * TSP 算法旅行推销员问题
 * 给定一系列城市和每对城市之间的距离，求解访问每一座城市一次并回到起始城市的最短回路。
 */
public class TspDp {
    public static void main(String[] args) {
        int[][] nums = {{Integer.MAX_VALUE, 3, 6, 7},
                {5, Integer.MAX_VALUE, 2, 3},
                {6, 4, Integer.MAX_VALUE, 2},
                {3, 7, 5, Integer.MAX_VALUE}
        };
        TspDp tspDp = new TspDp();
        int minCost = tspDp.getMinCost(nums, nums.length);
        System.out.println(minCost);
    }

    /**
     * 获取最小花费
     *
     * @param nums 节点之间的花费
     * @param n    节点个数
     * @return
     */
    public int getMinCost(int[][] nums, int n) {
        int row = n;
        int col = (int) Math.pow(2, n - 1);
        int[][] dp = new int[row][col];
        List<List<Integer>> countLists = getCountLists(row, col);

        init(dp, nums, row);

        //遍历数组中的每一个数
        for (int j = 1; j < row; j++) { //循环遍历 数量 数组
            List<Integer> countList = countLists.get(j);
            //用来确定横坐标
            for (int k = 0; k < countList.size(); k++) {
                //纵坐标
                for (int i = 0; i < row; i++) {
                    Integer num = countList.get(k);
                    int currentMinCost = getCurrentMinCost(i, num, row, col, nums, dp);
                    dp[i][num] = currentMinCost;

                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
        return dp[0][col - 1];
    }

    /**
     * 获取一个 list
     * list[0] 表示 从 0 ~ col 中二进制 拥有 0 个 1 的数字组成的集合
     * list[1] 表示 从 0 ~ col 中二进制 拥有 1 个 1 的数字组成的集合
     *
     * @param row
     * @param col
     * @return
     */
    private List<List<Integer>> getCountLists(int row, int col) {
        List<List<Integer>> countLists = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            countLists.add(new ArrayList<>());
        }

        for (int i = 0; i < col; i++) {
            int count = bitCount(i);
            countLists.get(count).add(i);
        }

        return countLists;
    }

    /**
     * 获取一个整数 二进制 1 的个数
     *
     * @param n
     * @return
     */
    private int bitCount(int n) {
        int c;
        for (c = 0; n != 0; ++c) {
            n &= (n - 1); // 清除最低位的1
        }
        return c;
    }

    /**
     * 获取当前条件下的最小花费
     *
     * @param i
     * @param num
     * @param row
     */
    private int getCurrentMinCost(int i, int num, int row,
                                  int col, int[][] nums, int[][] dp) {
        int pow = (int) Math.pow(2, i - 1);

        if (num != col - 1 && (i == 0 || (pow & num) == pow)) {
            return Integer.MAX_VALUE;
        }

        int min = Integer.MAX_VALUE;
        for (int j = 1; j < row; j++) {
            int pow1 = (int) Math.pow(2, j - 1);
            if ((pow1 & num) == pow1 && nums[i][j] != Integer.MAX_VALUE) {
                int y = num - pow1;
                int cost = nums[i][j] + dp[j][y];
                if (cost < min) {
                    min = cost;
                }
            }
        }
        return min;
    }

    /**
     * 初始化 dp
     *
     * @param dp
     * @param nums
     * @param row
     */
    private void init(int[][] dp, int[][] nums, int row) {
        for (int i = 0; i < row; i++) {
            dp[i][0] = nums[i][0];
        }
    }
}