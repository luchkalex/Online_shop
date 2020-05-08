<#import "parts/common.ftl" as c>
<#import "parts/login_tmp.ftl" as l>

<@c.page>
    <div class="container mt-5">
        <h4 class="text-sm-center">Add new user</h4>
        <@l.login "/registration" true/>
    </div>
</@c.page>



