import "./Modal.css"
import {motion} from "framer-motion"

interface ModalProps {
    children: React.ReactNode,
    onClose: () => void
}

function Modal({children, onClose}: ModalProps) {
    return (
        <>
            <motion.div
                initial={{
                    opacity: 0
                }}
                animate={{
                    opacity: 1
                }}
                className="modal" onClick={onClose}/>
            <motion.div
                initial={{
                    scale: 0
                }}
                animate={{
                    scale: 1
                }}
                className="modal-content">
                {children}
            </motion.div>
        </>
    )
}

export default Modal;