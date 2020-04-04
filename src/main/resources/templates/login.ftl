<#import "parts/common.ftl" as c>
<#import "parts/login_tmp.ftl" as l>

<@c.page>
    <h4 class="text-sm-center">Login page</h4>
    ${message ! ""}
    <@l.login "/login" false/>
</@c.page>
