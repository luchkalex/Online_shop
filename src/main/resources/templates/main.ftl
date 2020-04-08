<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <form method="get" action="/main" class="form-inline">
            <label>
                <input type="text" name="filter" value="${filter ! ""}" placeholder="Find by Tag">
            </label>
            <button type="submit" class="ml-2 btn btn-primary">Найти</button>
        </form>
    </div>

    <#include "parts/messageEdit.ftl">
    <#include "parts/messageList.ftl">

</@c.page>