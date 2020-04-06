<#import "parts/common.ftl" as c>
<#import "parts/login_tmp.ftl" as l>

<@c.page>
    <h4 class="text-sm-center">Login page</h4>

<#-- FIXME: Message "Bad credentials" shows after registration and link with code activation-->

    <#if message??>
        <div class="alert alert-${messageType}">
            ${message}
        </div>
    <#elseif Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>


    <@l.login "/login" false/>
</@c.page>
