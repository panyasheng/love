package com.pan.love.system.service;

import com.pan.love.system.bean.vo.*;

import java.util.List;

/**
 * 用户
 *
 * @author pan
 * @date 2019/10/22
 */
public interface UserService {
    /**
     * 新增用户
     *
     * @author pan
     * @param user 新增用户参数
     * @date 2019/10/22
     * @return 新增条数
     */
    int addUser(AddUserVo user);

    /**
     * 批量删除用户
     *
     * @author pan
     * @param listId 数组用户id
     * @date 2019/10/22
     * @return
     */
    int deleteUser(List<String> listId);

    /**
     * 修改用户
     *
     * @author pan
     * @param updateUserVO 用户更新参数
     * @date 2019/10/22
     * @return
     */
    int updateUser(UpdateUserVO updateUserVO);

    /**
     * 查询用户
     *
     * @author pan
     * @param queryUserInVo 查询用户参数
     * @date 2019/10/22
     * @return 数组用户
     */
    List<QueryUserOutVo> queryUser(QueryUserInVo queryUserInVo);

    /**
     * 通过电话号码发送短信验证码登录
     *
     * @author pan
     * @param phone 电话号码
     * @date 2019/11/11
     * @return
     */
    boolean getCode(String phone);

    /**
     * 登录
     * @param loginUserInVo
     * @return token
     */
    String login(LoginUserInVo loginUserInVo);
}
