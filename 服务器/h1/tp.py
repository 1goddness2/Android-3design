import qrcode
from PIL import Image
qr = qrcode.QRCode(
    version=1,
    error_correction=qrcode.constants.ERROR_CORRECT_L,
    box_size=10,
    border=4,
)
data={'num':'0','name':'无极之剑','place':'哇奎恩','man':'易','phone':'和','descrip':'hah'}
qr.add_data(data)
qr.make(fit=True)
img = qr.make_image()
img.save('D:/123.png')