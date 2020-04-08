<a class="btn btn-primary my-3" data-toggle="collapse" href="#inputCollapse" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="inputCollapse">
    <div class="form-group">
        <form method="post" enctype="multipart/form-data">
            <label for="message_text" class="mt-2">Message</label>
            <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')} my-1 col-sm-4"
                   value="<#if message??>${message.text}</#if>" placeholder="Введите сообщение" id="message_text"/>

            <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
            </#if>

            <label for="tag" class="mt-2">Tag</label>
            <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')} my-1 col-sm-4"
                   placeholder="Тэг" id="tag"
                   value="<#if message??>${message.tag}</#if>">
            <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
            </#if>
            <div class="custom-file mt-2 col-sm-4">
                <input type="file" class="custom-file-input" id="customFile" name="file">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <button type="submit" class="btn btn-primary my-1">Save</button>
        </form>
    </div>
</div>