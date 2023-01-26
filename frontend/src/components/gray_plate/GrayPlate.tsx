import "./GrayPlate.css"

interface GrayPlateProps {
    children: React.ReactNode
}

function GrayPlate({children}: GrayPlateProps) {
    return (
        <div className="plate">
            {children}
        </div>
    )
}

export default GrayPlate