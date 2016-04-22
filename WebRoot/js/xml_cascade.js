/*
根据请求的XML获得数据并填充目标下拉列表框
@param toSelect 目标下拉列表框
@param url 请求的XML路径
*/
function fillSelect(toSelect, url)
{
    var xml = new ActiveXObject("MSXML.DOMDocument");
    xml.async = false;
    xml.load(url);
    var root = xml.documentElement;
    if (root)
    {
        var nodes = root.childNodes;
        if (nodes)
        {
            for (var i = 0; i < nodes.length; i++)
            {
                var opt = new Option(nodes[i].childNodes[1].text, nodes[i].childNodes[0].text);
                toSelect.options.add(opt);
            }
        }
    }
    else
    {
        throw new Error("获取XML数据时出现错误，URL：" + url);
    }
}

/*
清空目标下拉列表框，如果第一项的值为空则保留第一项。
@param toSelect 目标下拉列表框
*/
function clearSelect(toSelect)
{
	if (toSelect.options.length > 0 && toSelect.options[0].value.length == 0)
		toSelect.options.length = 1;
    else
        toSelect.options.length = 0;
}

/*
根据请求的XML获得数据并改变目标下拉列表框
@param toSelect 目标下拉列表框
@param url 请求的XML路径
*/
function changeSelect(toSelect, url)
{
    clearSelect(toSelect);
    fillSelect(toSelect, url);
}

/*
检测传入的多个参数中是否有为null或""的参数，有则返回true，否则返回false。
*/
function hasEmptyParams()
{
    for (var i = 0; i < arguments.length; i++)
    {
        if (!arguments[i])
        {
            return true;
        }
    }
    return false;
}

/*
根据地市查询片区信息并改变片区下拉列表框
@param toSelect 片区下拉列表框
@param city 地市编号
*/
function city_district(toSelect, city)
{
    var url = "../common/xml_city_district.jsp?city=" + city;
    changeSelect(toSelect, url)
}

/*
根据地市和代理商查询代理片区信息并改变片区下拉列表框
@param toSelect 片区下拉列表框
@param city 地市编号
@param agent 代理商
*/
function city_agent_district(toSelect, city, agent)
{
    var url = "../common/xml_city_agent_district.jsp?city=" + city + "&agent=" + agent;
    changeSelect(toSelect, url)
}

/**
根据代理商获取其代理的地市信息，并改变地市下拉列表框
@param toSelect 地市下拉列表框
@param agent 代理商编号
*/
function agent_city(toSelect, agent)
{
	var url = "../common/xml_agent_city.jsp?agent="+agent;
	changeSelect(toSelect, url);
}

/*
根据地市和片区查询代理商信息并改变代理商下拉列表框
@param toSelect 代理商下拉列表框
@param city 地市编号
@param district 片区编号
*/
function area_agent(toSelect, city, district,isDirectAgent)
{
    var url = "../common/xml_area_agent.jsp?city=" + city + "&district=" + district + "&isDirectAgent=" + isDirectAgent;
    changeSelect(toSelect, url)
}

/*
根据登录用户的体系层次、角色级别、角色类型获得体系层次信息并改变目标下拉列表框
@param toSelect 体系层次下拉列表框
@param grade 角色级别
@param type 角色类型
*/
function role_sys(toSelect, type) 
{   
    var url = "../common/xml_role_syslevel.jsp?type=" + type;
    changeSelect(toSelect, url)
}

/*
根据不同的地市改变交通罚款办理手续费下拉列表框
@param toSelect 体系层次下拉列表框
@param type 地市
曹国丰  20120218
*/
function fines_sys(toSelect, type) 
{  
    var url = "../common/xml_fines_syslevel.jsp?type=" + type;
    changeSelect(toSelect, url)
}

/*
根据卡类别查询卡面值信息并改变卡面值下拉列表框
@param toSelect 卡面值下拉列表框
@param cardType 卡类别
*/
function cardtype_cardfee(toSelect, cardType)
{	
    var url = "../common/xml_cardtype_cardfee.jsp?cardType=" + cardType;    
    changeSelect(toSelect, url)
}
/*
根据卡类别查询卡面值信息并改变卡面值下拉列表框
@param toSelect 卡面值下拉列表框
@param cardType 卡类别
*/
function wlt_area(toSelect, areaid)
{	
    var url = "../common/xml_wlt_area.jsp?areaid=" + areaid;    
    changeSelect(toSelect, url)
}
/*
根据代办业务类别和业务类型查询代办商品信息信息并改变卡面值下拉列表框
@param toSelect 代办商品下拉列表框
@param cardType 卡类别
@param service 业务类型
*/
function busiagenttype_goods(toSelect, type)
{	
    var url = "../common/xml_busiagenttype_goods.jsp?type=" + type;
    changeSelect(toSelect, url)
}

/*
根据卡面值查询地市信息并改变地市下拉列表框
@param toSelect 地市下拉列表框
@param cardFee 卡面值
*/
function cardfee_city(toSelect, cardFee)
{
	var url = "../common/xml_cardfee_city.jsp?cardFee="+cardFee;
	changeSelect(toSelect, url);
}

/*
根据卡面值和地市编号查询片区信息并改变片区下拉列表框
@param toSelect 片区下拉列表框
@param cardFee 卡面值
@param city 地市编号
*/
function cardfee_city_dist(toSelect, cardFee, city)
{
	var url = "../common/xml_cardfee_city_dist.jsp?cardFee="+cardFee+"&city="+city;
	changeSelect(toSelect, url);
}

/*
根据可退费返销业务类型查询交易方式并改变交易方式下拉列表框
@param toSelect 交易方式下拉列表框
@param service 业务类型
*/
function service_tradetype(toSelect, service)
{
    var url = "../common/xml_service_tradetype.jsp?service="+service;
    changeSelect(toSelect, url);
}

/*
根据客户编号获取客户下的所有资金账户
@param toSelect 交易方式下拉列表框
@param custid 客户编号
*/
function cust_fund(toSelect,custid)
{
    var url = "../common/xml_cust_fund.jsp?custid="+custid;
    changeSelect(toSelect, url);
}

/*
根据代办户账号获取代办户下的所有资金账户
@param toSelect 交易方式下拉列表框
@param termId 代办户账号
*/
function termUser_fund(toSelect,termId)
{
    var url = "../common/xml_termUser_fund.jsp?termId="+termId;
    changeSelect(toSelect, url);
}


/*
根据佣金规则类型和佣金规则组类型获得佣金规则组列表
@param toSelect 佣金规则组下拉列表框
@param commRuleType 佣金规则类型
@param commRuleGroupType 佣金规则组类型
*/
function ruletype_rulegroup(toSelect, commRuleType, commRuleGroupType)
{
    if (hasEmptyParams(commRuleType, commRuleGroupType))
    {
        clearSelect(toSelect);
        return;
    }
    var url = "../common/xml_commruletype_commrulegroup.jsp?groupType=" + commRuleGroupType + "&ruleType=" + commRuleType;
    changeSelect(toSelect, url);
}
