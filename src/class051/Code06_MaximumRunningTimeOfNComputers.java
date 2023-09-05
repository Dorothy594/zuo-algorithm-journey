package class051;

// 同时运行N台电脑的最长时间
// 你有 n 台电脑。给你整数 n 和一个下标从 0 开始的整数数组 batteries
// 其中第 i 个电池可以让一台电脑 运行 batteries[i] 分钟
// 你想使用这些电池让 全部 n 台电脑 同时 运行。
// 一开始，你可以给每台电脑连接 至多一个电池
// 然后在任意整数时刻，你都可以将一台电脑与它的电池断开连接，并连接另一个电池，你可以进行这个操作 任意次
// 新连接的电池可以是一个全新的电池，也可以是别的电脑用过的电池
// 断开连接和连接新的电池不会花费任何时间。
// 注意，你不能给电池充电。
// 请你返回你可以让 n 台电脑同时运行的 最长 分钟数。
// 测试链接 : https://leetcode.cn/problems/maximum-running-time-of-n-computers/
public class Code06_MaximumRunningTimeOfNComputers {

	public static long maxRunTime(int num, int[] arr) {
		int max = 0;
		long sum = 0;
		for (int x : arr) {
			max = Math.max(max, x);
			sum += x;
		}
		if (sum > (long) max * num) {
			// 所有电池的最大电量是max
			// 如果此时sum > (long) max * num，
			// 说明 : 最终的供电时间一定在 >= max，而如果最终的供电时间 >= max
			// 说明 : 对于最终的答案X来说，所有电池都是课上讲的"碎片拼接"的概念
			// 那么寻找 ? * num <= sum 的情况中，尽量大的 ? 即可
			// 即sum / num
			return sum / num;
		}
		int ans = 0;
		for (int l = 0, r = max, m; l <= r;) {
			m = l + ((r - l) >> 1);
			if (f(arr, num, m)) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static boolean f(int[] arr, int num, int time) {
		// 碎片总和
		long sum = 0;
		for (int x : arr) {
			if (x > time) {
				num--;
			} else {
				sum += x;
			}
			if (sum >= (long) num * time) {
				return true;
			}
		}
		return false;
	}

}