<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container form-group">

        <h3>Give a feedback to ${product.title}</h3>

        <form method="post">
            <label for="rating"></label>
            <input id="rating" min="1" max="5" value="<#if product.rating??>${product.rating}<#else>0</#if>"
                   class="mx-2 mt-2" type="range" onchange="showInputValue()" name="rating">
            <span class="font-weight-bold text-primary ml-2 mt-1 valueSpan" id="rating_value">0</span>

            <label for="feedback_text"></label>
            <input type="text" placeholder="Text of feedback" id="feedback_text" name="text">

            <input type="hidden" value="${product.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-3">Give a feedback</button>
        </form>
    </div>
</@c.page>