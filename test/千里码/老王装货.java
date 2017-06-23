package 千里码;


public class 老王装货
{
    public static void main(String[] args)
    {
        int[] weightKg = new int[] { 509, 838, 924, 650, 604, 793, 564, 651, 697, 649, 747, 787, 701, 605, 644 };
        knapsack(weightKg, weightKg, 5000);
    }

    public static int knapsack(int val[], int wt[], int W)
    {
        int N = wt.length;
        int[][] V = new int[N + 1][W + 1];
        for (int col = 0; col <= W; col++)
        {
            V[0][col] = 0;
        }
        for (int row = 0; row <= N; row++)
        {
            V[row][0] = 0;
        }
        for (int item = 1; item <= N; item++)
        {
            for (int weight = 1; weight <= W; weight++)
            {
                if (wt[item - 1] <= weight)
                {
                    V[item][weight] = Math.max(val[item - 1] + V[item - 1][weight - wt[item - 1]], V[item - 1][weight]);
                }
                else
                {
                    V[item][weight] = V[item - 1][weight];
                }
            }
        }

        int j = W;
        int length = N;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = length; i > 0; --i)
        {
            //转态转移的时候记录
            if (V[i][j] > V[i - 1][j])
            {
                stringBuilder.append(i).append("-");
                j = j - wt[i - 1];
            }
        }
        String result = stringBuilder.toString();
        System.out.println("choose index : " + result.substring(0, result.length() - 1));
        return V[N][W];
    }

}
