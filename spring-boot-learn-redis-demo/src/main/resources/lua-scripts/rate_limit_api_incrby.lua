--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2018/3/15
-- Time: 14:24
-- To change this template use File | Settings | File Templates.
-- 计算策略访问剩余次数
-- key 使用策略的应用标识
-- refreshInterval 策略的刷新间隔，用于设置过期时间。设置为0时则认为不过期
-- limitNum 策略限制的次数
local function rate_limit_api_incrby(key,refreshInterval,limitNum)

    if tonumber(redis.call("exists",key)) ==0 then
        redis.call("set",key,limitNum)
        if tonumber(refreshInterval) > 0 then
            redis.call("expire",key,refreshInterval)
        end
    end

    return redis.call("incrby",key,-1)
end

return rate_limit_api_incrby(KEYS[1],ARGV[1],ARGV[2])
