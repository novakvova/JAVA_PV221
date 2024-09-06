import {PhotoIcon, UserCircleIcon} from '@heroicons/react/24/solid'
import {useForm} from "antd/es/form/Form";
import {Form, Input, message, Modal, Upload, UploadFile} from "antd";
import {categoryService} from "../../../services/categoryService.ts";
import {RcFile, UploadChangeParam} from "antd/es/upload";
import {useState} from "react";

function PlusOutlined() {
    return null;
}

const CategoryCreatePage = () => {
    const [form] = useForm();

    const [previewOpen, setPreviewOpen] = useState<boolean>(false);
    const [previewImage, setPreviewImage] = useState('');
    const [previewTitle, setPreviewTitle] = useState('');

    // const onHandleSubmit = async (e) => {
    //     e.preventDefault();
    //
    //
    //     console.log("----Submit form----");
    // }
    //
    // console.log("----Render app----");



    const validateMessages = {
        required: '${label} is required!',
        types: {
            email: '${label} is not a valid email!',
            number: '${label} is not a valid number!',
        },
        number: {
            range: '${label} must be between ${min} and ${max}',
        },
    };

    const onFinish = async (formData: any) => {
        console.log("form submit", formData);
        if(!formData.id){
            const result = await categoryService.create(formData);
            if(result.status == 200){
                message.success(`Category ${formData.name} successfully added`)
            }
        }
    };

    return (
        <>
            <div className="py-12">
                <div className="mx-auto max-w-7xl px-6 lg:px-24">
                    <Form
                        form={form}
                        name="nest-messages"
                        onFinish={onFinish}
                        layout={"vertical"}
                        className=' d-flex flex-column'
                        validateMessages={validateMessages}
                    >
                        <p className="text-center text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
                            Створення категорії
                        </p>
                        <div className="space-y-12">
                            <div className="border-gray-900/10 pb-4">
                                <div className="mt-4">
                                    <div className="my-4">
                                        <Form.Item name='name' label="Назва" rules={[{ required: true }]}>
                                            <Input />
                                        </Form.Item>
                                    </div>
                                    <div className="my-4">
                                        <Form.Item
                                            label="Оберіть фото"
                                            valuePropName="file"
                                            name="file"
                                            // getValueFromEvent={normFile}
                                            getValueFromEvent={(e: UploadChangeParam) => {
                                                const image = e?.fileList[0] as any;
                                                return image?.originFileObj;
                                            }}
                                            rules={[{ required: true }]}>
                                            <Upload
                                                listType="picture-card"
                                                beforeUpload={() => false}
                                                accept="image/png, image/jpeg, image/webp"
                                                maxCount={1}
                                                onPreview={(file: UploadFile) => {
                                                    if (!file.url && !file.preview) {
                                                        file.preview = URL.createObjectURL(file.originFileObj as RcFile);
                                                    }

                                                    setPreviewImage(file.url || (file.preview as string));
                                                    setPreviewOpen(true);
                                                    setPreviewTitle(file.name || file.url!.substring(file.url!.lastIndexOf('/') + 1));
                                                }}

                                            >
                                                <button style={{ border: 0, padding: 0, background: 'transparent' }} type="button" />
                                                <div className='d-flex flex-column align-items-center'>
                                                    <PlusOutlined />
                                                    <span>Upload</span>
                                                </div>

                                            </Upload>
                                        </Form.Item>
                                    </div>
                                    <div className="my-4">
                                        <Form.Item name='description' label="Опис">
                                            <Input.TextArea />
                                        </Form.Item>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div className="flex items-center justify-end gap-x-6">
                            <button type="button" className="text-sm font-semibold leading-6 text-gray-900">
                                Cancel
                            </button>
                            <button
                                type="submit"
                                className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            >
                                Save
                            </button>
                        </div>
                    </Form>
                </div>
            </div>
            <Modal open={previewOpen} title={previewTitle} footer={null} onCancel={() => setPreviewOpen(false)}>
                <img alt="example" style={{width: '100%'}} src={previewImage}/>
            </Modal>
        </>
    );
}

export default CategoryCreatePage;