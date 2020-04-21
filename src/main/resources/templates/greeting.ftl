<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <h5>Hello, <#if user??>${user.username}<#else >Guest</#if></h5>
</@c.page>
