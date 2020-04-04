<#import "parts/common.ftl" as c>
<#import "parts/login_tmp.ftl" as l>

<@c.page>
    <h4 class="text-sm-center">Add new user</h4>
    ${message ! ""}
    <@l.login "/registration" true/>
</@c.page>



