package com.pan.love.system.mapper;

import com.pan.love.system.bean.vo.AddUserVo;
import com.pan.love.system.bean.vo.QueryUserInVo;
import com.pan.love.system.bean.vo.QueryUserOutVo;
import com.pan.love.system.bean.vo.UpdateUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 *
 * @author pan
 * @date 2019/10/16
 */
@Mapper
public interface UserMapper {
    /**
     * 新增用户
     *
     * @author pan
     * @param user 用户参数
     * @date 2019/10/16
     * @return 新增条数
     */
    int addUser(AddUserVo user);

    /**
     * 删除用户
     *
     * @author pan
     * @param listId 数组用户id
     * @date 2019/10/22
     * @return 删除数量
     */
    int deleteUser(@Param(value = "listId") List<String> listId);

    /**
     * 修改用户
     *
     * @author pan
     * @param updateUserVO 修改用户参数
     * @date 2019/10/22
     * @return 修改数目
     */
    int updateUser(UpdateUserVO updateUserVO);

    /**
     * 查找用户
     *
     * @author pan
     * @param queryUserInVo 查询用户参数
     * @date 2019/10/22
     * @return 数组用户
     */
    List<QueryUserOutVo> queryUser(QueryUserInVo queryUserInVo);
}
