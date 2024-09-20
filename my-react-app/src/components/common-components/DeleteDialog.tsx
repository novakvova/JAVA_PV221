import React, { useState } from "react";
import {Button, Modal} from "antd";

interface Props {
    title: string;
    description: string;
    onSubmit: () => void;
}

export const DeleteDialog: React.FC<Props> = ({
                                                  title,
                                                  description,
                                                  onSubmit,
                                              }) => {
    const [open, setOpen] = useState(false);
    const handleCancel = () => {
        setOpen(false);
    };

    const hundleConfirm = () => {
        handleCancel();
        onSubmit();
    }

    return (
        <>
            <Button onClick={() => setOpen(true)} danger type="primary">Delete</Button>

            <Modal open={open} title={title} footer={[
                <Button key="back" onClick={handleCancel}>
                    Скасувати
                </Button>,
                <Button key="submit" type="primary" onClick={hundleConfirm}>
                    Видалити
                </Button>
            ]} onCancel={handleCancel}>
                <p>{description}</p>
            </Modal>
        </>
    );
};
