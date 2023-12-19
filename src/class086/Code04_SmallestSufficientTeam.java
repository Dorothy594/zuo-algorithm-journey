package class086;

import java.util.Arrays;
import java.util.List;

// 最小的必要团队
// 作为项目经理，你规划了一份需求的技能清单req_skills
// 并打算从备选人员名单people中选出些人组成必要团队
// 编号为i的备选人员people[i]含有一份该备选人员掌握的技能列表
// 所谓必要团队，就是在这个团队中
// 对于所需求的技能列表req_skills中列出的每项技能，团队中至少有一名成员已经掌握
// 请你返回规模最小的必要团队，团队成员用人员编号表示
// 你可以按 任意顺序 返回答案，题目数据保证答案存在
// 测试链接 : https://leetcode.cn/problems/smallest-sufficient-team/
public class Code04_SmallestSufficientTeam {

	// 讲解080、081 - 状压dp
	public static int[] smallestSufficientTeam(String[] skills, List<List<String>> people) {
		Arrays.sort(skills);
		int n = skills.length;
		int m = people.size();
		int[] arr = new int[m];
		for (int i = 0; i < m; i++) {
			int status = 0;
			List<String> skill = people.get(i);
			skill.sort((a, b) -> a.compareTo(b));
			int p1 = 0;
			int p2 = 0;
			while (p1 < n && p2 < skill.size()) {
				int compare = skills[p1].compareTo(skill.get(p2));
				if (compare < 0) {
					p1++;
				} else if (compare > 0) {
					p2++;
				} else {
					status |= 1 << p1;
					p1++;
					p2++;
				}
			}
			arr[i] = status;
		}
		int[][] dp = new int[m][1 << n];
		for (int i = 0; i < m; i++) {
			Arrays.fill(dp[i], -1);
		}
		int size = f(arr, n, 0, 0, dp);
		int[] ans = new int[size];
		int ansi = 0;
		int i = 0;
		int status = 0;
		while (status != (1 << n) - 1) {
			if (i + 1 == m || dp[i][status] != dp[i + 1][status]) {
				ans[ansi++] = i;
				status |= arr[i];
			}
			i++;
		}
		return ans;
	}

	public static int f(int[] arr, int n, int i, int status, int[][] dp) {
		if (status == (1 << n) - 1) {
			return 0;
		}
		if (i == arr.length) {
			return Integer.MAX_VALUE;
		}
		if (dp[i][status] != -1) {
			return dp[i][status];
		}
		int p1 = f(arr, n, i + 1, status, dp);
		int p2 = Integer.MAX_VALUE;
		int next2 = f(arr, n, i + 1, status | arr[i], dp);
		if (next2 != Integer.MAX_VALUE) {
			p2 = 1 + next2;
		}
		int ans = Math.min(p1, p2);
		dp[i][status] = ans;
		return ans;
	}

}