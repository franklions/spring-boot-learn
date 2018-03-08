--
-- Created by IntelliJ IDEA.
-- User: flsh
-- Date: 2018/3/8
-- Time: 9:04
-- To change this template use File | Settings | File Templates.
-- 计算API 剩余可调用次数 返回剩余次数跟重置时间
-- appkey 限制的关键字 refreshInterval 限制间隔 limitNum 限制请求的数量
local function rate_limit_api_call(appKey,refreshInterval,limitNum)
    local current = redis.call("incr",appKey)
    local remainingTime = redis.call("ttl",appKey)
    if tonumber(current) == 1 then
        redis.call("expire",appKey,refreshInterval)
    end
    if remainingTime < 0 then
        remainingTime = 0
    end
    return {math.max(-1,limitNum - current),remainingTime}
end

return rate_limit_api_call(KEYS[1],ARGV[1],ARGV[2])