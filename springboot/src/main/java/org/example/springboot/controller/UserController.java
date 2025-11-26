package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.*;
import org.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;



    @GetMapping("/selectAll")
    public Result selectAll()
    {
        List<UserInfo> list=userService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectById/{user_id}")
    public Result selectById(@PathVariable Integer user_id)
    {
        UserInfo user = userService.selectById(user_id);
        if (user != null) {
            return Result.success("获取用户信息成功", user);
        } else {
            return Result.error(404, "未找到该用户信息");
        }
    }



    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        if (username == null || password == null) {
            return Result.error(400, "用户名和密码不能为空");
        }
        UserInfo user = userService.login(username, password);
        if (user != null) {
            return Result.success("登录成功", user);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserInfo userInfo) {
        int result = userService.registerUser(userInfo);
        if (result > 0) {
            return Result.success("注册成功", userInfo);
        } else {
            return Result.error(400, "注册失败");
        }
    }

    /**
     * 通过 user_id 获取用户评论
     * @param userId 用户 ID
     * @return 包含用户评论的结果对象
     */
    @GetMapping("/comments/{userId}")
    public Result getUserComments(@PathVariable Integer userId) {
        List<UserComment> comments = userService.getUserCommentsByUserId(userId);
        return Result.success("获取用户评论成功", comments);
    }

    /**
     * 通过 user_id 获取用户历史记录
     * @param userId 用户 ID
     * @return 包含用户历史记录的结果对象
     */
    @GetMapping("/history/{userId}")
    public Result getUserHistory(@PathVariable Integer userId) {
        List<UserHistory> historyList = userService.getUserHistoryByUserId(userId);
        return Result.success("获取用户历史记录成功", historyList);
    }

    /**
     * 通过 user_id 获取用户互动记录
     * @param userId 用户 ID
     * @return 包含用户互动记录的结果对象
     */
    @GetMapping("/interactions/{userId}")
    public Result getUserInteractions(@PathVariable Integer userId) {
        List<UserInteraction> interactions = userService.getUserInteractionsByUserId(userId);
        return Result.success("获取用户互动记录成功", interactions);
    }


    @GetMapping("/interaction-count/{userId}")
    public Result countUserInteractions(@PathVariable Integer userId) {
        UserInteractionCount count = userService.countUserInteractions(userId);
        return Result.success("获取用户互动数量成功", count);
    }


    @PostMapping("/updateInfo")
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
        int result = userService.updateUserInfo(userInfo);
        if (result > 0) {
            return Result.success("用户信息更新成功", userInfo);
        } else {
            return Result.error(400, "用户信息更新失败");
        }
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody Map<String, Object> params) {
        int userId = Integer.parseInt(params.get("user_id").toString());
        String oldPassword = params.get("oldPassword").toString();
        String newPassword = params.get("newPassword").toString();

        int result = userService.updatePassword(userId, oldPassword, newPassword);
        if (result > 0) {
            return Result.success("密码修改成功");
        } else {
            return Result.error(400, "旧密码错误，密码修改失败");
        }
    }

    /**
     * 获取用户收藏记录
     * @param userId 用户 ID
     * @return 包含用户收藏记录的结果对象
     */
    @GetMapping("/collections/{userId}")
    public Result getUserCollections(@PathVariable Integer userId) {
        List<UserInteraction> collections = userService.getUserCollectionsByUserId(userId);
        return Result.success("获取用户收藏记录成功", collections);
    }

    /**
     * 根据目标类型和目标 ID 查询评论，并获取评论用户的用户名和头像
     * @param targetType 目标类型
     * @param targetId 目标 ID
     * @return 包含评论信息、用户名和头像的评论列表
     */
    @GetMapping("/comments/withUserInfo")
    public Result getCommentsWithUserInfo(@RequestParam String targetType, @RequestParam int targetId) {
        List<Map<String, Object>> comments = userService.getCommentsWithUserInfo(targetType, targetId);
        return Result.success("获取评论成功", comments);
    }


    @GetMapping("/likes/{userId}")
    public Result getUserLikes(@PathVariable Integer userId) {
        List<UserInteraction> likes = userService.getUserLikesByUserId(userId);
        return Result.success("获取用户点赞记录成功", likes);
    }

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody Map<String, String> resetData) {
        String phone = resetData.get("phone");
        String email = resetData.get("email");
        String newPassword = resetData.get("newPassword");
        if (phone == null || email == null || newPassword == null) {
            return Result.error(400, "电话号码、邮箱和新密码不能为空");
        }
        int result = userService.resetPassword(phone, email, newPassword);
        if (result > 0) {
            return Result.success("密码重置成功");
        } else {
            return Result.error(400, "电话号码或邮箱验证失败，密码重置失败");
        }
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody Map<String, Object> data) {
        String userIdStr = (String) data.get("user_id");
        String avatar = (String) data.get("avatar");
        if (userIdStr == null || avatar == null) {
            return Result.error(400, "用户 ID 和头像地址不能为空");
        }
        try {
            int userId = Integer.parseInt(userIdStr);
            int result = userService.updateAvatar(userId, avatar);
            if (result > 0) {
                return Result.success("头像更新成功");
            } else {
                return Result.error(500, "头像更新失败");
            }
        } catch (NumberFormatException e) {
            return Result.error(400, "用户 ID 格式不正确");
        }
    }

}
