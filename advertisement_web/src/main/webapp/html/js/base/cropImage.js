$.cropImage = function (control, link, resetFileInput, handleSuccess) {
    var imageAreaObject;
    var isLoad = true; // 控制变量
    var destroyed = false;

    $('#cropCancel').on('click', function() {
        // location.href="/resume/detail.html";
        if (destroyed) {
            return;
        }
        destroy();
    });

    $('#cropOK').click(function() {
        if (destroyed) {
            return;
        }
        var selection = imageAreaObject.getSelection();
        if (selection.width == 0 || selection.height == 0){
            popWindow.showPopTipWithContent('#errorTip', '请选择需要裁剪的区域');
            return;
        }
        $.ajax({
            type: 'POST',
            url: link,
            data: {
                x: $('#x').val(),
                y: $('#y').val(),
                w: $('#w').val(),
                h: $('#h').val(),
                actualWidth: $('#actualWidth').val(),
                actualHeight: $('#actualHeight').val(),
                path: $('#path').val(),
                originalWidth: $('#originalWidth').val(),
                originalHeight: $('#originalHeight').val()
            },
            dataType: 'json',
            beforeSend: function() {},
            success: function(data) {
                destroy();
                if (data.retCode == '200') {
                    if (isNull(handleSuccess)) {
                        popWindow.showPopTipWithContent('#okTip', '上传成功');
                    } else {
                        handleSuccess(data);
                    }
                } else {
                    popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                    return false;
                }
            },
            error: function(){
                destroy();
                popWindow.showPopTipWithContent('#errorTip', '网络错误，请稍后再试');
            }
        });
    });

    function checkUploadImage(control) {
        $.uploadImage(control, '#imageUploadForm', 'imageIfr', 
            function(data){
                    $('#path').val(data.ret);
                    $('#originalWidth').val(data.width);
                    $('#originalHeight').val(data.height);
                    // $.checkImageLoad('#imageSelected', loadSuccess, data.ret);
                    $('#imageSelected').attr('src', data.ret);
                    loadSuccess();
                }, function(){
                    destroy();
                });
    }

    // 判断图片加载状况，加载完成后回调
    function loadSuccess(){
        popWindow.hidePopTip('#reminderTip');
        $('.jcrop-overlay').fadeIn(500);

        imageAreaObject = $('#imageSelected').imgAreaSelect({
            maxWidth: 600,
            maxHeight: 600,
            minWidth: 32,
            minHeight: 32,
            handles: true,
            aspectRatio: '1:1',
            onSelectChange: preview,
            instance: true,
            remove: false,
            disable: false,
            hide: false,
            x1: 0,
            y1: 0,
            x2: 120,
            y2: 120,
            onInit: function (img, selection) {
                $('#x').val(selection.x1);
                $('#y').val(selection.y1);
                $('#w').val(selection.width);
                $('#h').val(selection.height);
                $('#actualWidth').val(img.width);
                $('#actualHeight').val(img.height);
            },
            onSelectEnd: function (img, selection) {
                $('#x').val(selection.x1);
                $('#y').val(selection.y1);
                $('#w').val(selection.width);
                $('#h').val(selection.height);
                $('#actualWidth').val(img.width);
                $('#actualHeight').val(img.height);
            }
        });
        imageAreaObject.setSelection(0, 0, 120, 120);
    }

    function preview(img, selection) {
        var scaleX = 100 / (selection.width || 1);
        var scaleY = 100 / (selection.height || 1);
        //这里需要originalWidth originalHeight，为了算图像原始宽高比
        $('#imagePreview').css({
            width: Math.round(scaleX * originalWidth) + 'px',
            height: Math.round(scaleY * originalHeight) + 'px',
            marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
            marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
        });
    }

        
    function destroy() {
        destroyed = true;
        if (isNotNull(imageAreaObject)) {
            imageAreaObject.setOptions({
                remove: true,
                disable: true,
                hide: true
            });
            imageAreaObject.update();
            imageAreaObject = null;
        }
        $('.jcrop-overlay').fadeOut(500);
        var file = $('#imageFile');
        file.after(file.clone().val(""));
        file.remove();
        if (isNotNull(resetFileInput)) {
            resetFileInput();
        }
        popWindow.hidePopTip('#reminderTip');
    }
    
    checkUploadImage(control);
};
