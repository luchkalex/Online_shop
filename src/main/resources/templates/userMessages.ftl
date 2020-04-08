<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>

    <h2>${userChannel.username}</h2>

    <#if !isCurrentUser>
        <#if isSubscriber>
            <a href="/users/unsubscribe/${userChannel.id}" class="btn btn-secondary">Unsubscribe</a>
        <#else>
            <a href="/users/subscribe/${userChannel.id}" class="btn btn-info">Subscribe</a>
        </#if>
    </#if>

    <div class="container my-3">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscriptions</div>
                        <h3 class="card-text">
                            <a href="/users/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscribers</div>
                        <h3 class="card-text">
                            <a href="/users/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser && message??>
        <#include "parts/messageEdit.ftl">
    </#if>
    <#include "parts/messageList.ftl">
</@c.page>